package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
)

var userToItemsTakenMap = make(map[string]map[string]int)
var totalInventory = make(map[string]int)
var userList *UserList

func main() {
	temp := [4]string{"Nick", "Azaldin", "Andrew", "Yifan"}
	userList = &UserList{
		UserListArr: temp[:],
	}

	for _, user := range userList.UserListArr {
		userToItemsTakenMap[user] = make(map[string]int)
	}

	//GET
	http.HandleFunc("/", paramHandler)                     //get user's taken items
	http.HandleFunc("/Users", userListGETHandler)          //get user list
	http.HandleFunc("/Inventory", displayInventoryHandler) //get total inventory
	//POST
	http.HandleFunc("/ChangeInventory", modifyInventoryHandler)                 //modify inventory
	http.HandleFunc("/ChangeUsersTakenItems", modifyUsersTakenInventoryHandler) //modify taken inventory for some user
	http.ListenAndServe(":8080", nil)
}

func getJSONStr(jsonifyMe interface{}) string {
	bytes, err := json.Marshal(jsonifyMe)
	if err != nil {
		fmt.Println("invalid type")
		return ""
	}
	jsonStr := string(bytes)
	return jsonStr
}

func paramHandler(w http.ResponseWriter, r *http.Request) {

	param, ok := r.URL.Query()["user"]

	if !ok || len(param) < 1 {
		log.Println("Url Param 'key' is missing")
		fmt.Fprintf(w, "need url parameter")
		return
	}

	// Query()["key"] will return an array of items,
	// we only want the single item.
	user := param[0]

	userData, doesUserExists := userToItemsTakenMap[user]
	if doesUserExists {
		log.Println("Selected User " + string(user))
		fmt.Fprintf(w, getJSONStr(userData))
	} else {
		fmt.Println("user does not exist")
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("400 - User does not exist"))
	}

}

func userListGETHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "GET")

	fmt.Fprintf(w, getJSONStr(*userList))
}

func displayInventoryHandler(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	w.Header().Set("Access-Control-Allow-Origin", "*")
	w.WriteHeader(http.StatusOK)
	fmt.Fprintf(w, getJSONStr(totalInventory))
}

func modifyUsersTakenInventoryHandler(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "POST")
	decoder := json.NewDecoder(request.Body)
	var postRequestData ModifyUserInventoryPostRequestData
	err := decoder.Decode(&postRequestData)
	if err != nil {
		fmt.Println(err)
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("400 - Bad Request Check JSON, make sure this is a POST"))
	} else {
		w.WriteHeader(http.StatusOK)
	}

	userToModify := postRequestData.UserToModify
	keyToModify := postRequestData.KeyToModify
	operation := postRequestData.Operation

	targetItemMap := userToItemsTakenMap[userToModify]
	_, keyExists := targetItemMap[keyToModify]
	switch operation {
	case 0: //decrement
		if keyExists {
			targetItemMap[keyToModify]--
			totalInventory[keyToModify]++
			if targetItemMap[keyToModify] <= 0 {
				delete(targetItemMap, keyToModify)
			}
		}
	case 1: //increment
		if keyExists {
			targetItemMap[keyToModify]++
			totalInventory[keyToModify]--
		} else {
			targetItemMap[keyToModify] = 1
		}

	}

}

func modifyInventoryHandler(w http.ResponseWriter, request *http.Request) {
	w.Header().Set("Access-Control-Allow-Origin", "POST")
	decoder := json.NewDecoder(request.Body)
	var postRequestData ModifyInventoryPostRequestData
	err := decoder.Decode(&postRequestData)
	if err != nil {
		fmt.Println(err)
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("400 - Bad Request Check JSON, make sure this is a POST"))
	} else {
		w.WriteHeader(http.StatusOK)
	}
	defer request.Body.Close()

	keyToModify := postRequestData.KeyToModify
	operation := postRequestData.Operation
	if err != nil {
		w.WriteHeader(http.StatusBadRequest)
		w.Write([]byte("400 - Bad Request Check JSON"))
	}
	_, keyExists := totalInventory[keyToModify]
	switch operation {
	case 0: //decrement
		if keyExists {
			totalInventory[keyToModify]--
			if totalInventory[keyToModify] <= 0 {
				delete(totalInventory, keyToModify)
			}
		}
	case 1: //increment
		if keyExists {
			totalInventory[keyToModify]++
		} else {
			fmt.Println("add new item")
			totalInventory[keyToModify] = 1
		}

	}

}
