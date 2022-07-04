<?php 
	include '../connection.php';
	
	if (isset($_GET['id_kurir'])) {
	    $id_kurir = $_GET['id_kurir'];
    	$perintah = "SELECT p.*, pk.id AS id_pk FROM pengiriman p LEFT JOIN pengiriman_kurir pk ON p.id = pk.id_pengiriman WHERE pk.id IS NULL OR pk.id_kurir = '$id_kurir'";
    	$eksekusi = mysqli_query($connection, $perintah);
    	$cek = mysqli_affected_rows($connection);
    
    	$response["kode"] = 1;
    	$response["pesan"] = "Data Tersedia";
    	$response["data"] = array();
    	if($cek > 0){
    		while ($ambil = mysqli_fetch_object($eksekusi)) {
    			$f["id"] = $ambil->id;
    			$f["nama"] = $ambil->nama;
    			$f["alamat"] = $ambil->alamat;
    			$f["latitude"] = $ambil->latitude;
    			$f["longitude"] = $ambil->longitude;
    			$f["is_selected"] = !is_null($ambil->id_pk);
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