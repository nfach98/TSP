<?php
include '../connection.php';

$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	//$id = $_POST['id'];
	$nama = $_POST['nama'];
	$alamat = $_POST['alamat'];
	$latitude = $_POST['latitude'];
	$longitude = $_POST['longitude'];

	$perintah = "INSERT INTO pengiriman (nama, alamat, latitude, longitude) VALUES ('$nama', '$alamat', '$latitude', '$longitude')";
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