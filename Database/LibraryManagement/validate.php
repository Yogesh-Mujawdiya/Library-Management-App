<?php
	$response = array();
	$response['admin']="False";
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');
	$option=$_POST['login_option'];
	$pass=$_POST['password'];
	if($option=="Scholar")
	{
		$id=$_POST['scho'];
		$s="select * from scholardata where scholar_id= '$id' && password='$pass'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
		{
			$ss="select * from admin where ID= '$id'";
			$res=mysqli_query($con,$ss);
			$n=mysqli_num_rows($res);
			if($n==1)
				$response['admin']="True";
		    $r = $result->fetch_assoc();
			$response['username']=$r["name"];
			$response['email']=$r["email"];
			$response["status"] = "True";
			$response["message"] = "login successfully";
		}
		else
		{
			$response["status"] = "False";
			$response["message"] = "invalid information";
		}
	}
	else if($option=="Staff")
	{
		$id=$_POST['staff'];
		$s="select * from staffdata where staff_id= '$id' && password='$pass'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
	   	{
			$ss="select * from admin where ID= '$id'";
			$res=mysqli_query($con,$ss);
			$n=mysqli_num_rows($res);
			if($n==1)
				$response['admin']="True";
		    $r = $result->fetch_assoc();
			$response['username']=$r["name"];
			$response['email']=$r["email"];
			$response["status"] = "True";
			$response["message"] = "login successfully";
	   	}
		else
		{
			$response["status"] = "False";
			$response["message"] = "invalid information";
		}		
	}
	else if($option=="Student")
	{
		$id=$_POST['roll'];
		$s="select * from studentdata where roll_no= '$id' && password='$pass'";
		$result=mysqli_query($con,$s);
		$num=mysqli_num_rows($result);
		if($num==1)
	   	{

			$ss="select * from admin where ID= '$id'";
			$res=mysqli_query($con,$ss);
			$n=mysqli_num_rows($res);
			if($n==1)
				$response['admin']="True";
		    $r = $result->fetch_assoc();
			$response['username']=$r["name"];
			$response['email']=$r["email"];
			$response["status"] = "True";
			$response["message"] = "login successfully";
	   	}
		else
		{
			$response["status"] = "False";
			$response["message"] = "invalid information";
		}	
	}

	echo json_encode($response);
?>