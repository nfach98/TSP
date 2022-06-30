<?php
include '../connection.php';

$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$id = $_POST['id'];
	$nama = $_POST['nama'];
	$alamat = $_POST['alamat'];
	$latitude = $_POST['latitude'];
	$longitude = $_POST['longitude'];


	$perintah = "UPDATE pengiriman SET nama = '$nama', alamat = '$alamat', latitude = '$latitude', longitude = '$longitude' WHERE id = '$id'";
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