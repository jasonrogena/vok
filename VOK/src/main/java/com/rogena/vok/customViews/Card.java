package com.rogena.vok.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rogena.vok.R;

public class Card extends LinearLayout
{
    private LinearLayout cardParentLayout;
    private ImageView cardImageView;
    private LinearLayout cardTextLayout;
    private TextView cardPrimaryTextView;
    private TextView cardSecondaryTextView;

    private Context context;
    private Card thisCard;
    private boolean backgroundWithShadow;
    private int prevX;
    private int originalLMargin=-1;
    private int originalRMargin=-1;
    private float originalAlpha=-1;
    private float swipedOutThreshold;
    private long resetDuration;
    private long goneDuration;
    private long moveUpDuration;
    private Card nextCard;
    private boolean swippable;

    private OnSwipedOutListener onSwipedOutListener;

    public Card(Context context)
    {
        super(context);
        this.context=context;

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.card,this);

        init(null);
    }

    public Card(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        this.context=context;

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.card,this);

        init(attributeSet);
    }

    private void init(AttributeSet attributeSet)
    {
        //TODO:init children using ids
        Log.d("vok","init called");
        thisCard=this;
        cardParentLayout=(LinearLayout)findViewById(R.id.cardParentLayout);
        cardImageView=(ImageView)findViewById(R.id.cardImageView);
        cardTextLayout=(LinearLayout)findViewById(R.id.cardTextLayout);
        cardPrimaryTextView=(TextView)findViewById(R.id.cardPrimaryTextView);
        cardSecondaryTextView=(TextView)findViewById(R.id.cardSecondaryTextView);
        nextCard=null;

        if(attributeSet!=null)
        {
            TypedArray typedArray=context.getTheme().obtainStyledAttributes(attributeSet,R.styleable.Card,0,0);
            try
            {
                String primaryCardText=typedArray.getString(R.styleable.Card_primary_text);
                String secondaryCardText=typedArray.getString(R.styleable.Card_secondary_text);
                Drawable cardImageDrawable=typedArray.getDrawable(R.styleable.Card_image);
                backgroundWithShadow=typedArray.getBoolean(R.styleable.Card_background_with_shadow,true);
                swipedOutThreshold=typedArray.getFloat(R.styleable.Card_swiped_out_threshold,0);
                resetDuration=(long)typedArray.getInt(R.styleable.Card_reset_duration,0);
                goneDuration =(long)typedArray.getInt(R.styleable.Card_gone_duration,0);
                moveUpDuration=(long)typedArray.getInt(R.styleable.Card_move_up_duration,0);
                int nextCardId=typedArray.getResourceId(R.styleable.Card_next_card,-1);
                swippable=typedArray.getBoolean(R.styleable.Card_swippable,false);
                if(primaryCardText!=null)
                {
                    cardPrimaryTextView.setText(primaryCardText);
                }
                if(secondaryCardText!=null)
                {
                    cardSecondaryTextView.setText(secondaryCardText);
                }
                if(cardImageDrawable!=null)
                {
                    cardImageView.setImageDrawable(cardImageDrawable);
                }
                if(backgroundWithShadow)
                {
                    cardParentLayout.setBackgroundResource(R.drawable.card_background_shadow);
                }
                else
                {
                    cardParentLayout.setBackgroundResource(R.drawable.card_background);
                }
                if(nextCardId!=-1)
                {
                    nextCard=(Card)this.findViewById(nextCardId);
                }

            }
            finally
            {
               typedArray.recycle();
            }
        }
    }

    public void setPrimaryText(String primaryText)
    {
        cardPrimaryTextView.setText(primaryText);
    }

    public String getPrimaryText()
    {
        return cardPrimaryTextView.getText().toString().trim();
    }

    public void setSecondaryText(String secondaryText)
    {
        cardSecondaryTextView.setText(secondaryText);
    }

    public String getSecondaryText()
    {
        return cardSecondaryTextView.getText().toString().trim();
    }

    public void setImage(Drawable imageDrawable)
    {
        cardImageView.setImageDrawable(imageDrawable);
    }

    public void setBackgroundWithShadow(boolean backgroundWithShadow)
    {
        this.backgroundWithShadow=backgroundWithShadow;
        if(backgroundWithShadow)
        {
            cardParentLayout.setBackgroundResource(R.drawable.card_background_shadow);
        }
        else
        {
            cardParentLayout.setBackgroundResource(R.drawable.card_background);
        }
    }

    public boolean isBackgroundWithShadow()
    {
        return backgroundWithShadow;
    }

    public void setCardSecondaryTextView(float swipedOutThreshold)
    {
        this.swipedOutThreshold=swipedOutThreshold;
    }

    public void setSwipedOutThreshold(float swipedOutThreshold)
    {
        this.swipedOutThreshold=swipedOutThreshold;
    }

    public float getSwipedOutThreshold()
    {
        return  this.swipedOutThreshold;
    }

    public void setResetDuration(long duration)
    {
        this.resetDuration=duration;
    }

    public long getResetDuration()
    {
        return resetDuration;
    }

    public void setGoneDuration(long duration)
    {
        this.goneDuration =duration;
    }

    public long getGoneDuration()
    {
        return goneDuration;
    }

    public void setMoveUpDuration(long duration)
    {
        this.moveUpDuration=duration;
    }

    public long getMoveUpDuration()
    {
        return moveUpDuration;
    }

    public void setNextCard(Card nextCard)
    {
        this.nextCard=nextCard;
    }

    public Card getNextCard()
    {
        return this.nextCard;
    }

    public boolean isSwippable()
    {
        return swippable;
    }

    public void setSwippable(boolean swippable)
    {
        this.swippable=swippable;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(swippable)
        {
            int positionX=(int)event.getRawX();
            if(event.getAction()==MotionEvent.ACTION_DOWN)
            {
                prevX=positionX;
                if(originalLMargin==-1 && originalRMargin==-1 && originalAlpha==-1)
                {
                    LayoutParams layoutParams=(LayoutParams)this.getLayoutParams();
                    originalLMargin=layoutParams.leftMargin;
                    originalRMargin=layoutParams.rightMargin;
                    originalAlpha=this.getAlpha();
                }
            }
            else if(event.getAction()==MotionEvent.ACTION_MOVE)
            {
                int deltaX=positionX-prevX;
                LayoutParams layoutParams=(LayoutParams)this.getLayoutParams();
                layoutParams.rightMargin=layoutParams.rightMargin-deltaX;
                layoutParams.leftMargin=layoutParams.leftMargin+deltaX;
                this.setLayoutParams(layoutParams);
                prevX=positionX;

                float c=0;
                float y=0;
                if(layoutParams.leftMargin>layoutParams.rightMargin)
                {
                    c=layoutParams.leftMargin;
                    y=getDisplayWidth()-originalLMargin;
                }
                else
                {
                    c=layoutParams.rightMargin;
                    y=getDisplayWidth()-originalRMargin;
                }
                float x=getDisplayWidth()-c;
                float alpha=1;
                if(x>0)
                {
                    alpha=x/y;
                }
                else
                {
                    alpha=0;
                }
                this.setAlpha(alpha);

            }
            else if(event.getAction()==MotionEvent.ACTION_UP)
            {
                if(this.getAlpha()> swipedOutThreshold)
                {
                    runResetAnimation();
                }
                else
                {
                    runGoneVisibilityAnimation();
                    //this.setVisibility(View.GONE);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    private int getDisplayWidth()
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    public interface OnSwipedOutListener
    {
        public void onSwipedOut(Card card);
    }

    public void setOnSwipedOutListener(OnSwipedOutListener onSwipedOutListener)
    {
        this.onSwipedOutListener=onSwipedOutListener;
    }

    private void runGoneVisibilityAnimation()
    {
        //TranslateAnimation heightAnimation=new TranslateAnimation(0,0,this.getHeight(),0);
        LayoutParams layoutParams=(LayoutParams)thisCard.getLayoutParams();
        TranslateAnimation translateAnimation=null;
        if(layoutParams.rightMargin>layoutParams.leftMargin)//go to the left
        {
            translateAnimation=new TranslateAnimation(0,layoutParams.rightMargin-getDisplayWidth(),0,0);
        }
        else//go to the right
        {
            translateAnimation=new TranslateAnimation(0,getDisplayWidth()-layoutParams.leftMargin,0,0);
        }
        translateAnimation.setDuration(goneDuration);
        translateAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                int cardHeight=thisCard.getHeight();
                thisCard.clearAnimation();
                LayoutParams layoutParams=(LayoutParams)thisCard.getLayoutParams();
                layoutParams.leftMargin=originalLMargin;
                layoutParams.rightMargin=originalRMargin;
                thisCard.setLayoutParams(layoutParams);
                thisCard.setAlpha(originalAlpha);
                thisCard.setVisibility(View.GONE);
                if(onSwipedOutListener!=null)
                {
                    onSwipedOutListener.onSwipedOut(thisCard);
                }
                if(nextCard!=null)
                {
                    nextCard.runMoveUpAnimation(cardHeight);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        this.clearAnimation();
        this.startAnimation(translateAnimation);
    }

    private void runResetAnimation()
    {
        final LayoutParams layoutParams=(LayoutParams)this.getLayoutParams();

        TranslateAnimation marginAnimation=new TranslateAnimation(0,(layoutParams.rightMargin - originalRMargin),0,0);
        marginAnimation.setDuration(resetDuration);
        float initAlpha=this.getAlpha()+0.45f;//it was noticed that there was some blinking if this was not done
        AlphaAnimation alphaAnimation=new AlphaAnimation(initAlpha,originalAlpha);//TODO: check if this is right
        alphaAnimation.setDuration(resetDuration);

        AnimationSet animationSet=new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(marginAnimation);

        animationSet.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                thisCard.clearAnimation();
                layoutParams.rightMargin=originalRMargin;
                layoutParams.leftMargin=originalLMargin;
                thisCard.setLayoutParams(layoutParams);
                thisCard.setAlpha(originalAlpha);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        this.clearAnimation();
        this.startAnimation(animationSet);
    }

    public void runMoveUpAnimation(float previousCardHeight)
    {
        TranslateAnimation moveUpAnimation=new TranslateAnimation(0,0,previousCardHeight,0);
        moveUpAnimation.setDuration(moveUpDuration);
        moveUpAnimation.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation)
            {
                //TODO: consider calling the moveupanimation on the next card
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        this.clearAnimation();
        this.startAnimation(moveUpAnimation);
    }

}