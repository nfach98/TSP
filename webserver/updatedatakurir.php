<?php
include 'connection.php';

$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$id = $_POST['id'];
	$name = $_POST['name'];
	$address = $_POST['address'];
	$no = $_POST['notelp'];


	$perintah = "UPDATE kurir SET name = '$name', address = '$address', notelp = '$no' WHERE id = '$id'";
	$eksekusi = mysqli_query($connection, $perintah);
	$cek = mysqli_affected_rows($connection);

	if ($cek > 0) {
		$response["kode"] = 1;
		$response["pesan"] = "Data Berhasil Diubah";
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Gagal Ubah Data";
	}
	
} else {
	$response["kode"] = 0;
	$response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);
?>