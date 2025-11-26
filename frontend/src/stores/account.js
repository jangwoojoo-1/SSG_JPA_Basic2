//계정 스토어 구현 : 계정 상태 정보 저장
//frontend/src/stores/account.js
import {defineStore} from 'pinia'

// Pinia에서 제공하는 defineStore라는 메서드를 호출해서 스토어를 정의한 것,
// 계정 데이터를 다루는 계정 스토어
export const useAccount = defineStore("account", { // ①
    state: () => ({
        id: 0,
        checked: false, // ②
        loggedIn: false, // ③
    }),
    actions: {
        setId(val){
          this.id = val;
        },
        setChecked(val) { // ④
            this.checked = val;
        },
        setLoggedIn(val) { // ⑤
            this.loggedIn = val;
        },
    },
});
