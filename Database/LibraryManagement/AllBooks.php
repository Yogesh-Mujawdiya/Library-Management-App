<?php
	$response = array();
	session_start();
	$con=mysqli_connect('localhost','root','','library');
	mysqli_select_db($con,'library');

	$Info = array();
	$R=mysqli_query($con,"SELECT * FROM bookdata");
	$i=0;
	while ($I=mysqli_fetch_array($R)) {
		$Info[$i]["title"] = $I["title"] ;
		$Info[$i]["author"] = $I["author"] ;
		$Info[$i]["access_no"] = $I["access_no"] ;
		$Info[$i]["call_no"] = $I["call_no"] ;
		$Info[$i]["availability"] = $I["availability"] ;
		$i+=1;
	}
	$response["list"] = $Info;
	$response["status"] = "True";
	$response["message"] = "Data Update !";
	echo json_encode($response);
?>