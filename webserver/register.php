<?php
	include 'connect.php';

	if ($_POST) {
		$username = filter_input(INPUT_POST, 'username', FILTER_SANITIZE_STRING);
		$password = filter_input(INPUT_POST, 'password', FILTER_SANITIZE_STRING);
		$name = filter_input(INPUT_POST, 'name', FILTER_SANITIZE_STRING);

		$response = [];

		//cek username dalam database
		$userQuery = $connection->prepare("SELECT * FROM user WHERE username = ?");
		$userQuery->execute(array($username));

		//cek username apakah ada atau tidak
		if ($userQuery->rowCount() != 0) {
			//Beri response
			$response['status'] = false;
			$response['message'] = "Akun sudah digunakan";
		} else {
			$insertAccount = "INSERT INTO user (name, username, password) VALUES (:name, :username, :password)";
			$statement = $connection->prepare($insertAccount);

			try {
				//eksekusi statement db
				$statement->execute([
					':name' => $name,
					':username' => $username,
					':password' => md5($password)]);
				
				$response['status'] = true;
				$response['message'] = "Akun berhasil didaftar";
				$response['data'] = [
					'username' => $username,
					'name' => $name];

			} catch (Exception $e) {
				die($e->getMessage());
			}
		}

		$json = json_encode($response, JSON_PRETTY_PRINT);
		echo $json;
	}
	
?>