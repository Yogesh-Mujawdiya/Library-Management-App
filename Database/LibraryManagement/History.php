<?php
	$response = array();
	session_start();
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');

	$Info = array();
	$Id=$_POST['Id'];
	$R=mysqli_query($con,"SELECT * FROM books WHERE issued_to_id='$Id'");
	$i=0;
	while ($I=mysqli_fetch_array($R)) {
		if($I["returned_on"]!="0000-00-00"){
			$Info[$i]["title"] = $I["title"] ;
			$Info[$i]["access_no"] = $I["access_no"] ;
			$Info[$i]["issue_date"] = $I["issue_date"] ;
			$Info[$i]["returned_on"] = $I["returned_on"] ;
			$i+=1;
		}
	}

	$response["list"] = $Info;
	$response["status"] = "True";
	$response["message"] = "Data Update !";
	echo json_encode($response);
?>