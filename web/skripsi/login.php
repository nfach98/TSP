<?php
	include 'connect.php';

	if ($_POST) {
		// Data
		$username=$_POST['Username'] ?? '';
		$password=$_POST['Password'] ?? '';

		$response=[]; //data response

		//cek username didalam database
		$userQuery = $connection->prepare("SELECT * FROM user WHERE Username = ?");
		$userQuery->execute(array($username));
		$query = $userQuery->fetch(); 

		if ($userQuery->rowCount() == 0) {
			$response['status'] = false;
			$response['message'] = "Username Tidak Terdaftar";
		} else {
			//ambil password DB
			$passwordDB = $query['Password'];

			if (strcmp(md5($password), $passwordDB) === 0) {
				$response['status'] = true;
				$response['message'] = "Login Berhasil";
				$response['data'] = [
					'user_id' => $query['id'],
					'username' => $query['username'],
					'name' => $query['name']];
			} else {
				$response['status'] = false;
				$response['message'] = "Password anda salah";
			}
		}
	
	//jadikan data JSON
	$json = json_encode($response, JSON_PRETTY_PRINT);
	//PRINT
	echo $json;	
	} 
	
?>