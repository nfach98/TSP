<?php 

include 'connection.php';

$response = array();
$username = $_POST['username'];
$password = $_POST['password'];

$perintah = "SELECT * FROM user WHERE username='$username'";
$eksekusi = mysqli_query($connection, $perintah);
$cek = mysqli_affected_rows($connection);

if ($cek == 0) {
	$response["kode"] = 0;
	$response["pesan"] = "Username Tidak Terdaftar";
} else {
	$perintah2 = "SELECT * FROM user WHERE username='$username' AND password='$password'";
	$eksekusi2 = mysqli_query($connection, $perintah2);
	$cek2 = mysqli_affected_rows($connection);

	if ($cek2 > 0) {
		$response["kode"] = 1;
		$response["pesan"] = "Login Berhasil";
		$response["data"] = array();

		while ($ambil = mysqli_fetch_object($eksekusi2)) {
			$f["id"] = $ambil->Id;
			$f["username"] = $ambil->Username;
			$f["status"] = $ambil->stts;

			array_push($response["data"], $f);
		}
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Password Salah!";
	}	
}

echo json_encode($response);
mysqli_close($connection);
?>
