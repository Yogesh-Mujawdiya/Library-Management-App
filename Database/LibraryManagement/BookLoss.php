<?php
	$response = array();
	$response["status"] = "False";
	session_start();
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');
	$option=$_POST['options'];
	$access=$_POST['access'];
	$s=0;
	if($option=="Stu3")
	{
		$id=$_POST['roll'];
		global $s;
		$s="select * from studentdata where roll_no= '$id' ";
	}
	else if($option=="Scho3")
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
				$q=0;
				$res = $result->fetch_assoc();
				$avl=$res["availability"];
				$title=$res["title"];
				if($avl=="unavailable")
				{
					$q="select * from books where access_no= '$access' and issued_to_id='$id' ORDER BY issue_date DESC LIMIT 1";
					$limit+=1;
					$result=mysqli_query($con,$q);
					$res = $result->fetch_assoc();
					$issue_date=$res["issue_date"];
					if(mysqli_num_rows($result))
					{
						$replacement=$_POST['replacement'];
						if($replacement=="bookrep")
						{
							$new_title=$_POST['new_title'];
							$new_access=$_POST['new_access'];
							$s="INSERT INTO `bookloss`(`id`, `title`, `access_no`, `lost_by_id`, `lost_by_name`, `replaced`,`replace_with_book`, `replaced_book_access`) VALUES ('','$title','$access','$id','$name','YES','$new_title','$new_access')";
							if(mysqli_query($con,$s))
							{
								$s="delete from bookdata where access_no='$access'";
								if(mysqli_query($con,$s))
								{
									$response["status"] = "True";
									$response["message"] = "record successfully now add new book's data to database";
								}
							}
						}
						else
						{
							$fine=$_POST['fine'];
							$s="INSERT INTO `bookloss`(`id`, `title`, `access_no`, `lost_by_id`, `lost_by_name`, `replaced`, `fine`) VALUES ('','$title','$access','$id','$name','NO','$fine')";
							if(mysqli_query($con,$s))
							{
								$response["message"] = "record successfully";
								$s="delete from bookdata where access_no='$access'";
								if(mysqli_query($con,$s))
									$response["status"] = "True";
							}
						}
						$s="update books set returned_on=sysdate() where access_no= '$access' and issued_to_id='$id' and issue_date='$issue_date'";
						mysqli_query($con,$s);
						if($option=="Stu3")
						{
							$s="update studentdata set book_limit='$limit' where roll_no= '$id'";
							mysqli_query($con,$s);
						}
						else if($option=="Scho3")
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
		$response["message"] = "User not registered";


	echo json_encode($response);
?>