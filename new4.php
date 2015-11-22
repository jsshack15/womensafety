<?php
   function getAddress($lat, $lon){
   $url  = "http://maps.googleapis.com/maps/api/geocode/json?latlng=".
            $lat.",".$lon."&sensor=false";
   $json = @file_get_contents($url);
   $data = json_decode($json);
   $status = $data->status;
   $address = '';
   if($status == "OK"){
      $address = $data->results[0]->formatted_address;
    }
   return $address;
  }

  # Call function
  $servername="localhost";
    $username = "root";
$password = "";
$dbname = "women_sec";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
$sql="select * from `user_data`";
$result = mysqli_query($conn,$sql);
while($row = mysqli_fetch_array($result))
{	$u=$row['long'];
	$r=$row['lat'];
	$q=$row['mobile_no'];
	$t=$row['msg'];
	if($t==0)
	{
		$msg="unsafe";
	}
	else
	{$msg="PANIC";}

$sql1="select name from `user_admn` WHERE `mobile_no`='$q'";
$result1 = mysqli_query($conn,$sql1);
$row2 = mysqli_fetch_array($result1);
$name=$row2['name'];
  echo getAddress($u,$r)."<br>".$name."<br>".$msg."<br>";
  $page = $_SERVER['PHP_SELF'];
$sec = "2";
}

 ?>
 
 <meta http-equiv="refresh" content="<?php echo $sec?>;URL='<?php echo $page?>'">
 
 