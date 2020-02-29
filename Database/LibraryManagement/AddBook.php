<?php
	$response = array();
	$response["status"] = "False";
	session_start();
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');
	$title=$_POST['title'];
	$author=$_POST['author'];
	$access=$_POST['access'];
	$call=$_POST['call'];
	$publication=$_POST['publication'];
	$cost=$_POST['cost'];
	$edition=$_POST['edition'];
	$s="select * from bookdata where access_no= '$access' ";
	$result=mysqli_query($con,$s);
	$num=mysqli_num_rows($result);
	if($num==1)
		$response["message"] = "Access no. already exist";
	else
		{
			$reg="insert into `bookdata`(`title`, `author`, `access_no`, `call_no`, `publication`, `cost`, `edition`, `purchase_date`) values('$title','$author','$access','$call','$publication','$cost','$edition',sysdate())";
			mysqli_query($con,$reg);
			$response["message"] = "book added successfully";
			$response["status"] = "True";
		}

	echo json_encode($response);
?>