package main

type TestResponse struct {
	Test int `json:"test"`
}

type PostRequestJSONData struct {
	KeyToModify string `json:"keyToModify`
	ValueToSet  int    `json:"valueToSet`
}
