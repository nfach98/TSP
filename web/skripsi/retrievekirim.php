<?php 
	include 'connection.php';

	$perintah = "SELECT * FROM pengiriman";
	$eksekusi = mysqli_query($connection, $perintah);
	$cek = mysqli_affected_rows($connection);

	if($cek > 0){
		$response["kode"] = 1;
		$response["pesan"] = "Data Tersedia";
		$response["data"] = array();

		while ($ambil = mysqli_fetch_object($eksekusi)) {
			$f["id"] = $ambil->id;
			$f["nama"] = $ambil->nama;
			$f["alamat"] = $ambil->alamat;
			$f["latitude"] = $ambil->latitude;
			$f["longtitude"] = $ambil->longitude;

			array_push($response["data"], $f);
		}
	} else {
		$response["kode"] = 0;
		$response["pesan"] = "Data Tidak Tersedia";
	}

	echo json_encode($response);
	mysqli_close($connection);
?>