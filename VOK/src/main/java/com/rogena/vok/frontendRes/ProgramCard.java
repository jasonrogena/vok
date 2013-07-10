package com.rogena.vok.frontendRes;

import android.content.Context;

import com.rogena.vok.backendRes.Program;
import com.rogena.vok.customViews.Card;

/**
 * Created by jason on 7/10/13.
 */
public class ProgramCard extends Card
{
    private final Program program;
    public ProgramCard(Context context, Program program)
    {
        super(context);
        this.program=program;
    }

    //TODO: check when card is in focus so as to start fetching image
}
