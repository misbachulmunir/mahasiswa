<?php
require("koneksi.php");

$response=array();

//jika ada yang idikirim
if($_SERVER["REQUEST_METHOD"]=="POST"){
//tangkap data
$nama=$_POST["nama"];
$tgl_lahir=$_POST["tgl_lahir"];
$jenis_kelamin=$_POST["jenis_kelamin"];
$jurusan=$_POST["jurusan"];
$alamat=$_POST["alamat"];

//masukan data
$perintah = "insert into tbl_mahasiswa (nama,tgl_lahir,jenis_kelamin,jurusan,alamat) values('$nama','$tgl_lahir','$jenis_kelamin','$jurusan','$alamat')";
$eksekusi=mysqli_query($konek,$perintah);
$cek=mysqli_affected_rows($konek);
//jika cek berhasil
if($cek>0){
	$response["kode"]=1;
	$response["pesan"]="Simpan Data Berhasil";
}
else{
$response["kode"]=0;
	$response["pesan"]="Gagal Menyimpan Data";
}


}

//jika tidak ada
else{
	$response["kode"]=0;
	$response["pesan"]="Tidak Ada Post Data";
}

echo json_encode($response);
mysqli_close($konek);
