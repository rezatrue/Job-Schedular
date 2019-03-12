<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Max-Age: 3600");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
 
// get database connection
include_once '../config/database.php';
 
// instantiate product object
include_once '../objects/tasklog.php';


$database = new Database();
$db = $database->getConnection();
 
$tasklog = new Tasklog($db);
 
// get posted data
$data = json_decode(file_get_contents("php://input"));

// set product property values
$tasklog->empid = $data->empid;
$tasklog->taskid = $data->taskid;
$tasklog->starttime = $data->starttime;
$tasklog->endtime = $data->endtime;
$tasklog->keycount = $data->keycount;
$tasklog->mousecount = $data->mousecount;
$tasklog->image = $data->image;

// inserting new restaurant data  
if($tasklog->logtask()){
	// copy image 
	$imageName = '../images/'. $data->empid . '/'. $data->image . '.png';
	$imageData = base64_decode($data->base64encodedImage);
	file_put_contents($imageName, $imageData);
	// response status
	$status = "ok";
	$taskid = $tasklog->taskid;
}else {
	$status = "failed";
	$taskid = "null";
}

echo json_encode(array("response" => $status ,"tasks" => $taskid));

?>