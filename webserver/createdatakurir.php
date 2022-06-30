<?php
include 'connection.php';

$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$id = $_POST['id'];
	$nama = $_POST['name'];
	$alamat = $_POST['address'];
	$no = $_POST['notelp'];

	$perintah = "INSERT INTO kurir VALUES ('$id', '$nama', '$alamat', '$no')";
	$eksekusi = mysqli_query($connection, $perintah);
	$cek = mysqli_affected_rows($connection);

	if ($cek > 0) {
		$response["kode"] = 1;
		$response["pesan"] = "Simpan Data Berhasil";
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Gagal Menyimpan Data";
	}
	
} else {
	$response["kode"] = 0;
	$response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);
?>