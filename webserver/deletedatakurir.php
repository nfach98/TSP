<?php
include 'connection.php';

$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$id = $_POST['id'];

	$perintah = "DELETE FROM kurir WHERE id = '$id'";
	$eksekusi = mysqli_query($connection, $perintah);
	$cek = mysqli_affected_rows($connection);

	if ($cek > 0) {
		$response["kode"] = 1;
		$response["pesan"] = "Hapus Data Berhasil";
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Gagal Hapus Data";
	}
	
} else {
	$response["kode"] = 0;
	$response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);
?>