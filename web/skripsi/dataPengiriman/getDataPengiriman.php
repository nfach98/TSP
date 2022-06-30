<?php
include '../connection.php';

$response = array();
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$id = $_POST['id'];

	$perintah = "SELECT * FROM pengiriman WHERE id = '$id'";
	$eksekusi = mysqli_query($connection, $perintah);
	$cek = mysqli_affected_rows($connection);

	if ($cek > 0) {
		$response["kode"] = 1;
		$response["pesan"] = "Data Tersedia";
		$response["data"] = array();

		while ($ambil = mysqli_fetch_object($eksekusi)) {
			$f["id"] = $ambil->id;
			$f["nama"] = $ambil->nama;
			$f["alamat"] = $ambil->alamat;
			$f["latitude"] = $ambil->latitude;
			$f["longitude"] = $ambil->longitude;

			array_push($response["data"], $f);
		}
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Data Tidak Tersedia";
	}
	
} else {
	$response["kode"] = 0;
	$response["pesan"] = "Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($connection);
?>