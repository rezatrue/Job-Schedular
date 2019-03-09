<?php
// required headers
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET");

// include database and restaurant files
include_once '../config/database.php';
include_once '../objects/tasklog.php';

// instantiate database and product object
$database = new Database();
$db = $database->getConnection();

$tasklog = new Tasklog($db);

if($_SERVER['REQUEST_METHOD'] == 'GET') {
$empid =  isset($_GET['emp_id']) ? $_GET['emp_id'] : null;
$starttime =  isset($_GET['start_time']) ? $_GET['start_time'] : null;
$endtime =  isset($_GET['end_time']) ? $_GET['end_time'] : null;
}

$num = 0;

// query products
if($empid != null){
	$stmt = $tasklog->searchTask($empid, $starttime, $endtime);
	$num = $stmt->rowCount();
}

// check if more than 0 record found
if($num>0){

	// products array
	$task_arr=array();
	$task_arr["task"]=array();

	// retrieve our table contents
	// fetch() is faster than fetchAll()
	// http://stackoverflow.com/questions/2770630/pdofetchall-vs-pdofetch-in-a-loop
	while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
		// extract row
		// this will make $row['name'] to
		// just $name only
		extract($row);

		$task_info=array(
			"serialno" => $empid,
			"imageurl" => $taskid,
			"name" => $starttime,
			"type" => $keycount,
			"address" => $mousecount,
			"phone" => $image
		);

		array_push($task_arr["task"], $task_info);
	}

	echo json_encode($task_arr);
}

else{
    echo json_encode(
		array("message" => "No task found.")
	);
}
?>