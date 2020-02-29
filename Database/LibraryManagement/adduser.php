<?php
	$response = array();
	session_start();
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');
	$option=$_POST['options'];
	$name=$_POST['user'];
	$pass=$_POST['pass'];
	$email=$_POST['email'];

	if($option=="Scholar")
	{
		$id=$_POST['scho'];
		$s="select * from scholardata where scholar_id= '$id'  or email='$email'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
		{
			$response["status"] = "False";
			$response["message"] = "user ID already exist";
		}
		else
		{
			$_SESSION['username']=$name;
			$_SESSION['id']=$id;
			$reg="insert into scholardata values('','$id','$name','$pass','$email',2)";
			mysqli_query($con,$reg);
			$response["status"] = "True";
			$response["message"] = "user added successfully";
		}
	}
	else if($option=="Student")
	{
		$id=$_POST['roll'];
		$course=$_POST['course'];
		$year=$_POST['year'];
		$s="select * from studentdata where roll_no= '$id' or email='$email'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
		{
			$response["status"] = "False";
			$response["message"] = "user ID already exist";
		}
		else
		{
			$_SESSION['username']=$name;
			$_SESSION['id']=$id;
			$reg="insert into studentdata values('','$id','$name','$pass','$email',2,'$course','$year')";
			mysqli_query($con,$reg);
			$response["status"] = "True";
			$response["message"] = "user added successfully";	
		}
	}
	else if($option=="Staff")
	{
		$id=$_POST['staff'];
		$s="select * from staffdata where staff_id= '$id' or email='$email'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
		{
			$response["status"] = "False";
			$response["message"] = "user ID already exist";
		}
		else
		{
			$_SESSION['username']=$name;
			$_SESSION['id']=$id;
			$reg="insert into staffdata values('','$id','$name','$pass','$email')";
			mysqli_query($con,$reg);
			$response["status"] = "True";
			$response["message"] = "user added successfully";	
		}
	}
	
	echo json_encode($response);
?>