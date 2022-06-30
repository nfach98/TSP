<?php
include '../connection.php';

$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$id_pengiriman = $_POST['id_pengiriman'];
	$id_kurir = $_POST['id_kurir'];
	$status = $_POST['status'];

	$perintah = "UPDATE pengiriman_kurir SET status = $status WHERE id_pengiriman = $id_pengiriman AND id_kurir = '$id_kurir'";
	$eksekusi = mysqli_query($connection, $perintah);
	$cek = mysqli_affected_rows($connection);

	if ($cek > 0) {
		$response["kode"] = 1;
		$response["pesan"] = "Data Berhasil Diubah";
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Data Gagal Diubah";
	}
	
} else {
	$response["kode"] = 0;
	$response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);
?>