<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");

// include database and object files
include_once '../config/database.php';
include_once '../objects/employee.php';

// instantiate database and product object
$database = new Database();
$db = $database->getConnection();

// initialize object
$employee = new Employee($db);

/*
// please apply post later
if($_SERVER['REQUEST_METHOD'] == 'GET') {
$emp_email =  isset($_GET['email']) ? $_GET['email'] : null;
$emp_password = isset($_GET['password']) ? $_GET['password'] : null;
}
*/

// get posted data
$data = json_decode(file_get_contents("php://input"));
$emp_email = $data->email;
$emp_password = $data->password;


// query employee
$stmt = $employee->login($emp_email, $emp_password); // need to pass parameter
$num = $stmt->rowCount();

	$status = "failed";
// emplyee array
	$emp_arr=array();
	$emp_arr["employee"]=array();
	$emp_info= null; 
	
// check if more than 0 record found
if($num > 0){
	
	// retrieve our table contents
	// fetch() is faster than fetchAll()
	// http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
	while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){	
		
		$empid = $row['empid'];
		$empName = $row['empName'];
		$empEmail = $row['empEmail'];
		$empPhone = $row['empPhone'];
		$empCreated = $row['empCreated'];
		$empActive = $row['empActive'];
		$empLevel = $row['empLevel'];
		
		$emp_info=array(
			"empid" => $empid,
			"empName" => $empName,
			"empEmail" => $empEmail,
			"empPhone" => $empPhone,
			"empCreated" => $empCreated,
			"empActive" => $empActive,
			"empLevel" => $empLevel,
		);
	}
	if($empid != null) 
		$status = "ok";	
}
	
array_push($emp_arr["employee"], $emp_info);

echo json_encode(array("response" => $status ,"employee" => $emp_arr["employee"]));

?>