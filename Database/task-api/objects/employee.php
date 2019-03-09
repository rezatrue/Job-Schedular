<?php
class Employee{

	// database connection and table name
	private $conn;
	private $table_name = "employee_details";

	// Emplyee properties
	public $empid;
	public $empName;
	public $empEmail;  
	public $empPassword; 
	public $empPhone; 
	public $empCreated;
	public $empActive; 
	public $empLevel;	
	
	// constructor with $db as database connection
	public function __construct($db){
		$this->conn = $db;
	}
	
	// read users
	function read(){

	// select all query
	$query = "SELECT
				empid, empName, empEmail, empPassword, empPhone, empCreated, empActive, empLevel
			FROM
				" . $this->table_name . " ORDER BY empCreated ASC";

	// prepare query statement
	$stmt = $this->conn->prepare($query);
	// execute query
	$stmt->execute();

	return $stmt;
}


	// user search with id
	function searchemplyee($empPhone){

	// select all query
	$query = "SELECT empid, empName, empEmail, empPassword, empPhone, empCreated, empActive, empLevel FROM
				" . $this->table_name . " WHERE empPhone = ? ";

	// prepare query statement
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $empPhone);
	// execute query
	$stmt->execute();

	return $stmt;
}

	// login user
	function login($email, $password){

	// select all query
	$query = "SELECT empid, empName, empEmail, empPhone, empCreated, empActive, empLevel FROM 
				" . $this->table_name . " WHERE empEmail = ? AND empPassword = ? ORDER BY empid ASC";

	// prepare query statement
	$stmt = $this->conn->prepare($query);
	$stmt->bindParam(1, $email);
	$stmt->bindParam(2, $password);
	// execute query
	$stmt->execute();

	return $stmt;
}


	// create product
	function createemplyee(){
 
    // query to insert record
    $query = "INSERT INTO
                " . $this->table_name . "
            SET
                empName=:name, empEmail=:email, empPassword=:password, empPhone=:phone, empCreated=:created, empActive=:active, empLevel=:level";
			
    // prepare query
    $stmt = $this->conn->prepare($query);
 
    // sanitize
    $this->empName=htmlspecialchars(strip_tags($this->empName));
	$this->empEmail=htmlspecialchars(strip_tags($this->empEmail));
	$this->empPassword=htmlspecialchars(strip_tags($this->empPassword));
    $this->empPhone=htmlspecialchars(strip_tags($this->empPhone));
    $this->empCreated=htmlspecialchars(strip_tags($this->empCreated));
	$this->empActive=htmlspecialchars(strip_tags($this->empActive));
	$this->empLevel=htmlspecialchars(strip_tags($this->empLevel));
    
	// bind values
    $stmt->bindParam(":name", $this->empName);
	$stmt->bindParam(":email", $this->empEmail);
	$stmt->bindParam(":password", $this->empPassword);
    $stmt->bindParam(":phone", $this->empPhone);
    $stmt->bindParam(":created", $this->empCreated);
	$stmt->bindParam(":active", $this->empActive);
	$stmt->bindParam(":level", $this->empLevel);

	
    // execute query
    if($stmt->execute()){
        return true;
    }
 
    return false;
     
}
	
	
}