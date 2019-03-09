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
include_once '../objects/employee.php';
 
$database = new Database();
$db = $database->getConnection();
 
$employee = new Employee($db);
 
// get posted data
$data = json_decode(file_get_contents("php://input"));

// set user property values
$employee->empName = $data->empName;
$employee->empEmail = $data->empEmail;
$employee->empPassword = $data->empPassword;
$employee->empPhone = $data->empPhone;
$employee->empCreated = $data->empCreated;
$employee->empActive = $data->empActive;
$employee->empLevel = $data->empLevel;


if($employee->createemplyee()){
	$status = "ok";
	$name = $employee->empName;
}else {
	$status = "failed";
	$name = "null";
}

echo json_encode(array("response" => $status ,"name" => $name));

?>