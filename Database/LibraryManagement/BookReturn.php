<?php
	$response = array();
	$response["status"] = "False";
	session_start();
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');
	$option=$_POST['options'];
	$access=$_POST['access'];
	$s=0;
	if($option=="Stu2")
	{
		$id=$_POST['roll'];
		global $s;
		$s="select * from studentdata where roll_no= '$id' ";
	}
	else if($option=="Scho2")
	{
		$id=$_POST['scho'];
		global $s;
		$s="select * from scholardata where scholar_id= '$id' ";
	}
	else
	{
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
		if($limit!=2)
		{
			$s="select * from bookdata where access_no= '$access'";
			$result=mysqli_query($con,$s);
			$num=mysqli_num_rows($result);
			if($num==1)
			{	
				$res = $result->fetch_assoc();
				$avl=$res["availability"];
				if($avl=="unavailable")
				{
					$q="select * from books where access_no= '$access' and issued_to_id='$id' ORDER BY issue_date DESC LIMIT 1";
					$limit+=1;
					$result=mysqli_query($con,$q);
					if(mysqli_num_rows($result))
					{
						$res = $result->fetch_assoc();
						$diff = strtotime(date("Y-m-d")) - strtotime($res["issue_date"]); 
						$issue_date=$res["issue_date"];
						$diff=round($diff / (60 * 60 * 24));
						$s="update bookdata set availability='available' where access_no= '$access'";
						mysqli_query($con,$s);
						$s="update books set returned_on=sysdate() where access_no= '$access' and issued_to_id='$id' and issue_date='$issue_date'";
						mysqli_query($con,$s);
						if($diff>30 && $option!="Staff")
						{
							$fine=($diff-30)*.5;
							$response["status"] = "True";
							$response["message"] = "you have to pay ".$fine." rupees";
							$s="insert into fine values('','$access','$id','$name','$fine',sysdate())";
							mysqli_query($con,$s);								
						}
						else
						{
							$response["status"] = "True";
							$response["message"] = "returned successfully";
						}
						if($option=="Stu2")
						{
							$s="update studentdata set book_limit='$limit' where roll_no= '$id'";
							mysqli_query($con,$s);
						}
						else if($option=="Scho2")
						{
							$s="update scholardata set book_limit='$limit' where scholar_id= '$id'";
							mysqli_query($con,$s);
						}
					}
					else
						$response["message"] = "invalid information";
				}
				else
					$response["message"] = "not yet issued";
			}
			else
				$response["message"] = "Access No. invalid";
		}
		else
			$response["message"] = "no book issued";
   	}
	else
		$response["message"] = "User not registered".$option;

	echo json_encode($response);
?>