<?php
$username="";
if(isset($_GET['arg0'])){$username = $_GET['arg0'];}
if($username != ""){
    $data = file_get_contents("https://api.mojang.com/users/profiles/minecraft/".$username);
    $array = json_decode($data, true);
    $id = $array["id"];
    $data = file_get_contents("https://api.mojang.com/user/profiles/".$id."/names");
    $array = json_decode($data, true);
    $return = "Username Supplied: ".$username."<br />\nUUID: ".$id."<br />\n";
    $return .= "------------------------------------------<br />\n";
    foreach($array as $uname){
        if(isset($uname["changedToAt"])){
            $return .= $uname["name"]." (Changed: " . date("jS F Y @ g:i", intval(substr($uname["changedToAt"], 0, strlen($uname["changedToAt"]) - 3))) . " GMT)<br />\n";
        } else {
            $return .= $uname["name"]." (Original Name)<br />\n";
        }
    }
    print($return);
}
?>