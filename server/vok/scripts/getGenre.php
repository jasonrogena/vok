<?php
    if(file_exists("settings.ini"))
    {
        $settings=array();
        $settings=parse_ini_file("settings.ini");
        $username=$settings['username'];
        $password=$settings['password'];
        $dbName=$settings['db_name'];
        $host=$settings['host'];
        mysql_connect($host,$username,$password);
        mysql_select_db($dbName);
        $databaseVersion=$_POST['database_version'];
        if($databaseVersion==1)
        {
            $genreID=$_POST['genre_id'];
            $query="SELECT * FROM `genre` WHERE id=".$genreID;
            $result=mysql_query($query);
            $jsonArray=array();
            if($fetchedRow=mysql_fetch_array($result))
            {
                $jsonArray['id']=$fetchedRow['id'];
                $jsonArray['name']=$fetchedRow['name'];
                $jsonArray['date_added_m']=$fetchedRow['date_added_m'];
                $json=json_encode($jsonArray);
                echo $json;
            }
            else
            {
                echo "upt0d@te";
            }
        }
    }
?>