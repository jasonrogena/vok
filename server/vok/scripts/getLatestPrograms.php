<?php
	if(file_exists("settings.ini"))
	{
	    //TODO: add functionality for userPrograms of course after google authentication
		$settings=array();
		$settings=parse_ini_file("settings.ini");
		$username=$settings['username'];
		$password=$settings['password'];
		$dbName=$settings['db_name'];
		$host=$settings['host'];
		mysql_connect($host,$username,$password);
		mysql_select_db($dbName);
		$lastUpdated=$_POST['last_updated'];
		$databaseVersion=$_POST['database_version'];
		if($databaseVersion==1)
		{
			$programArray=array();
			$query="SELECT * FROM `program` WHERE `date_added_m` > ".$lastUpdated;
			$result=mysql_query($query);
			$count=0;
			while($fetchedProgram=mysql_fetch_array($result))
			{
				$programArray[$count]=array();
				$programArray[$count]['id']=$fetchedProgram['id'];
				$programArray[$count]['name']=$fetchedProgram['name'];
				$programArray[$count]['station_id']=$fetchedProgram['station_id'];
				$programArray[$count]['genre_id']=$fetchedProgram['genre_id'];
				$programArray[$count]['start_date']=$fetchedProgram['start_date'];
				$programArray[$count]['image_url']=$fetchedProgram['image_url'];
				$programArray[$count]['date_added_m']=$fetchedProgram['date_added_m'];
				$count=$count+1;
			}
			if($programArray[0]!=null)
			{
				$programJson=json_encode($programArray);
				echo $programJson;
			}
			else
			{
				echo "upt0d@te";
			}
		}
	}
?>
