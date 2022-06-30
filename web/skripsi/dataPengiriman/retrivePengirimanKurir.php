<?php 
	include '../connection.php';
	
	if (isset($_GET['id_kurir'])) {
	    $id_kurir = $_GET['id_kurir'];
        $perintah = "SELECT pengiriman.id, pengiriman.nama, pengiriman.alamat, pengiriman.latitude, pengiriman.longitude, pengiriman_kurir.status FROM pengiriman_kurir INNER JOIN pengiriman ON pengiriman_kurir.id_pengiriman = pengiriman.id WHERE id_kurir='$id_kurir'";
    	$eksekusi = mysqli_query($connection, $perintah);
    	$cek = mysqli_affected_rows($connection);
    
    	$response["kode"] = 1;
    	$response["pesan"] = "Data Tersedia";
    	$response["data"] = array();
    	if($cek > 0){
    		while ($ambil = mysqli_fetch_object($eksekusi)) {
    			$f["id"] = (int) $ambil->id;
    			$f["nama"] = $ambil->nama;
    			$f["alamat"] = $ambil->alamat;
    			$f["latitude"] = (float) $ambil->latitude;
    			$f["longitude"] = (float) $ambil->longitude;
    			$f["status"] = (int) $ambil->status;
    			array_push($response["data"], $f);
    		}
    	} else {
    		$response["kode"] = 0;
    		$response["pesan"] = "Data Tidak Tersedia";
    	}
	} else {
	    $response["kode"] = 0;
		$response["pesan"] = "Data Tidak Tersedia";
	}

	echo json_encode($response);
	mysqli_close($connection);
?>