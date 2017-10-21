package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
)

var itemDatabase map[string]int = make(map[string]int)
var t PostRequestJSONData
var jsonStr string

func main() {
	itemDatabase["test"] = 10
	jsonStr = getJSONStr()
	http.HandleFunc("/Modify-ItemData", handlePOST)
	http.HandleFunc("/ItemData", handleGET)
	http.ListenAndServe(":8080", nil)

}

func getJSONStr() string {
	bytes, _ := json.Marshal(itemDatabase)
	jsonStr = string(bytes)
	return jsonStr
}

func handleGET(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	fmt.Fprintf(w, jsonStr)
}

func handlePOST(w http.ResponseWriter, request *http.Request) {
	decoder := json.NewDecoder(request.Body)
	
	err := decoder.Decode(&t)
	if err != nil {
		fmt.Println(err)
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("400 - Bad Request Check JSON"))
	} else {
		w.WriteHeader(http.StatusOK)
	}
	defer request.Body.Close()
	log.Println(t.KeyToModify)
}
