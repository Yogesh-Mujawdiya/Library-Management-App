<?php
	$response = array();
	$response["status"] = "False";
	session_start();
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');
	$option=$_POST['options'];
	$access=$_POST['access'];
	$s=0;
	$id=0;
	if($option=="Stu1")
	{
		global $id;
		$id=$_POST['roll'];
		global $s;
		$s="select * from studentdata where roll_no= '$id' ";
	}
	else if($option=="Scho1")
	{
		global $id;
		$id=$_POST['scho'];
		global $s;
		$s="select * from scholardata where scholar_id= '$id' ";
	}
	else
	{
		global $id;
		$id=$_POST['staff'];
		global $s;
		$s="select * from staffdata where staff_id= '$id' ";
	}
	$result=mysqli_query($con,$s);
	$num=mysqli_num_rows($result);
	if($num==1)
   	{
		$r = $result->fetch_assoc();
		$name=$r["name"];
		$limit=1;
		if($option!="Staff")
			$limit=$r["book_limit"];
		if($limit!=0)
		{
			$s="select * from bookdata where access_no= '$access'";
			$result=mysqli_query($con,$s);
			$num=mysqli_num_rows($result);
			if($num==1)
			{
				$res = $result->fetch_assoc();
				$avl=$res["availability"];
				if($avl=="available")
				{
					$call=$res["call_no"];
					$title=$res["title"];
					$s=0;
					if($option=="Stu1")
					{
						global $s;
						$s="insert into books values('','$title','$call','$access','$id','$name','Student',sysdate(),'')";
					}
					else if($option=="Scho1")
					{
						global $s;
						$s="insert into books values('','$title','$call','$access','$id','$name','Scholar',sysdate(),'')";
					}
					else
					{
						global $s;
						$s="insert into books values('','$title','$call','$access','$id','$name','Staff',sysdate(),'')";
					}
					if(mysqli_query($con,$s))
					{
						$s="update bookdata set availability='unavailable' where access_no= '$access'";
						mysqli_query($con,$s);
						$limit-=1;
						$response["status"] = "True";
						$response["message"] = "book issued";
						if($option=="Stu1")
						{
						    $s="update studentdata set book_limit='$limit' where roll_no= '$id'";
							mysqli_query($con,$s);
						}
						else if($option=="Scho1")
						{
							$s="update scholardata set book_limit='$limit' where scholar_id= '$id'";
							mysqli_query($con,$s);
						}					
					}
					else
						$response["message"] = "something went wrong";
				}
				else
					$response["message"] = "already issued";
					
			}
			else
				$response["message"] = "Access No. invalid";
		}
		else
			$response["message"] = "book issue limit exceed";
   	}
	else
		$response["message"] = "User not registered";


	echo json_encode($response);
?>