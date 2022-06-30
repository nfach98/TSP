<?php

$connection = null;
try {
	$host = "localhost";
	$username = "ninofach_skripsi";
	$password = "p59UUEk7YinHCzS";
	$dbName = "ninofach_skripsi";

	$database = "mysql:dbname=$dbName;host=$host";
	$connection = new PDO($database, $username, $password);
	$connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

/*	if ($connection) {
		// code...
		echo "Koneksi Berhasil";
	} else {
		// code...
		echo "Koneksi Gagal";
	}*/
	
} catch (Exception $e) {
	echo "Error!".$e->getMessage;
	die;
}

?>