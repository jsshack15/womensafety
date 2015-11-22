<?PHP

 
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

$q = $_GET['q'];
$u = $_GET['u'];
$r = $_GET['r'];

$sql="UPDATE `user_data` SET `long`='$u',`lat`='$r' WHERE `mobile_no`='$q'";
$result = mysqli_query($conn,$sql);
if($result)
{
	echo "done";
	session_start();
}
echo mysqli_error($conn);





 ?>