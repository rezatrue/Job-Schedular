<?php
class Tasklog{

	// database connection and table name
	private $conn;
	private $table_name = "task_log";

	// Task Log properties
	public $empid;
	public $taskid;
	public $starttime;
	public $endtime;
	public $keycount;
	public $mousecount;
	public $image;
	
	// constructor with $db as database connection
	public function __construct($db){
		$this->conn = $db;
	}
	
	// read Task Log
	function read(){

	// select all query
	$query = "SELECT
				empid, taskid, starttime, endtime, keycount, mousecount, image
			FROM
				" . $this->table_name . " ORDER BY empid ASC";

	// prepare query statement
	$stmt = $this->conn->prepare($query);
	// execute query
	$stmt->execute();

	return $stmt;
}


	// user search with id
	function searchAllTask($empid){

	// select all query
	$query = "SELECT empid, taskid, starttime, endtime, keycount, mousecount, image FROM
				" . $this->table_name . " WHERE empid = ? ORDER BY starttime ASC";

	// prepare query statement
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $empid);
	// execute query
	$stmt->execute();

	return $stmt;
}

	// search task
	function searchTask($empid, $start, $end){

	// select all query
	$query = "SELECT empid, taskid, starttime, endtime, keycount, mousecount, image FROM 
				" . $this->table_name . " WHERE empid = ? AND starttime BETWEEN ? AND ? ORDER BY starttime ASC";

	// prepare query statement
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $empid);
	$stmt->bindParam(2, $start);
	$stmt->bindParam(3, $end);
	// execute query
	$stmt->execute();

	return $stmt;
}

	// save task
	function logtask(){
 
    // query to insert record
    $query = "INSERT INTO
                " . $this->table_name . "
            SET
                empid=:empid, taskid=:taskid, starttime=:start, endtime=:ends, keycount=:keys, mousecount=:mouses, image=:image;"
		
    // prepare query
    $stmt = $this->conn->prepare($query);
 
    // sanitize
    $this->empid=htmlspecialchars(strip_tags($this->empid));
	$this->taskid=htmlspecialchars(strip_tags($this->taskid));
	$this->starttime=htmlspecialchars(strip_tags($this->starttime));
    $this->endtime=htmlspecialchars(strip_tags($this->endtime));
    $this->keycount=htmlspecialchars(strip_tags($this->keycount));
	$this->mousecount=htmlspecialchars(strip_tags($this->mousecount));
	$this->image=htmlspecialchars(strip_tags($this->image));
    
	// bind values
    $stmt->bindParam(":empid", $this->empid);
	$stmt->bindParam(":taskid", $this->taskid);
	$stmt->bindParam(":start", $this->starttime);
    $stmt->bindParam(":ends", $this->endtime);
    $stmt->bindParam(":keys", $this->keycount);
	$stmt->bindParam(":mouses", $this->mousecount);
	$stmt->bindParam(":image", $this->image);

	
    // execute query
    if($stmt->execute()){
        return true;
    }
 
    return false;
     
}
	
	
}