<?php 
include 'connection.php';

//if($_SERVER['REQUEST_METHOD'] == 'POST'){
	$perintah = "SELECT * FROM pengiriman";
	$eksekusi = mysqli_query($connection, $perintah);
	$cek = mysqli_affected_rows($connection);
	$jmlPengiriman = mysqli_num_rows($eksekusi);

	if($cek > 0){
		$response["kode"] = 1;
		$response["pesan"] = "Data Tersedia";
		$response["total"] = $jmlPengiriman;
		$response["data"] = array();

		while ($ambil = mysqli_fetch_object($eksekusi)) {
			$f["id"] = $ambil->id;
			$f["latitude"] = $ambil->latitude;
			$f["longitude"] = $ambil->longitude;

			array_push($response["data"], $f);
		}
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Data Tidak Tersedia";
	}
/*} else {
	$response["kode"] = 0;
	$response["pesan"] = "Tidak Ada Post Data";
}*/
	
echo json_encode($response);
mysqli_close($connection);
?>