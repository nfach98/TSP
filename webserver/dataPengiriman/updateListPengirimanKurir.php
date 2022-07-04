<?php
include '../connection.php';

$response = array();
$ids = $_POST["ids"];
$id_kurir = $_POST["id_kurir"];

$del = "DELETE FROM pengiriman_kurir WHERE id_kurir = '$id_kurir'";
$eksekusi = mysqli_query($connection, $del);
$cek = mysqli_affected_rows($connection);

if(isset($_POST["ids"])) {
    $ids = explode(",", $ids);
	$insert = "INSERT INTO pengiriman_kurir (id_pengiriman, id_kurir) VALUES ";
	foreach ($ids as $id) {
      $insert = $insert . "($id,'$id_kurir'),";
    }
    $insert = substr($insert, 0, -1);
    $eksekusi = mysqli_query($connection, $insert);
    $cek_ins = mysqli_affected_rows($connection);
    if ($cek_ins > 0) {
		$response["kode"] = 1;
		$response["pesan"] = "Update Data Berhasil";
	}
	else {
		$response["kode"] = 0;
		$response["pesan"] = "Update Data Gagal";
	}
} else {
    $response["kode"] = 1;
	$response["pesan"] = "Update Data Berhasil";
}

echo json_encode($response);
mysqli_close($connection);
?>