package rsa

import (
	"flag"
	"log"
	"testing"
)

func TestGenRsaKey(t *testing.T) {
	//生成密钥
	var bits int
	flag.IntVar(&bits, "b", 1024, "密钥长度，默认为1024位")
	pub, pri, err := GenRsaKey(bits)
	if err != nil {
		t.Fatal("密钥文件生成失败！")
	}
	//t.Log(pub)
	//t.Log(pri)
	log.Println(pub)
	t.Log("密钥文件生成成功！")

	source := "admin"

	//加密
	ds, err := RsaEncrypt([]byte(source), []byte(pub))
	if err != nil {
		t.Fatal(err.Error())
	}
	s1 := string(ds)
	t.Log(s1)
	t.Log("加密成功！")
	//解密
	es, err := RsaDecrypt(ds, []byte(pri))
	if err != nil {
		t.Fatal(err.Error())
	}
	s2 := string(es)
	t.Log(s2)
	t.Log("解密成功！")
}
