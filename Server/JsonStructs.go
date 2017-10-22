package main

type UserList struct {
	UserListArr []string `json:"userList"`
}

type ModifyInventoryPostRequestData struct {
	KeyToModify string `json:"keyToModify"`
	Operation   int    `json:"operation"` //0 = decrement, 1 = increment
}

type ModifyUserInventoryPostRequestData struct {
	UserToModify string `json:"userToModify"`
	KeyToModify  string `json:"keyToModify"`
	Operation    int    `json:"operation"` //0 = decrement, 1 = increment
}
