<script setup>
import {addItem} from "@/services/cartService";
import {useRouter} from "vue-router";
import {computed} from "vue";


// 프로퍼티 객체 : 부모컴포넌트로부터 전달받을 데이터 정의, 이름과 데이터 타입을 지정한다.
const props = defineProps({ // ①
  item: {
    id: Number,
    imgPath: String,
    name: String,
    price: Number,
    discountPer: Number,
    comment: String
  }
});

// 상품 할인가 : 상품의 할인가 컴퓨티드 , 프로퍼티의 데이터를 기준으로 상품의 할인가를
// 자동 계산하고 결괏값을 반환한다. 결괏값에 쉼표를 추가한다.
const computedItemDiscountPrice = computed(() => { // ②
  return (props.item.price - (props.item.price * props.item.discountPer / 100)).toLocaleString() + '원';
})

// 라우터 객체
const router = useRouter();

// 장바구니에 상품 담기 : 장바구니에 상품을 추가하는 메서드 :추후 구현
const put = async () => { // ②
  const res = await addItem(props.item.id);

  if (res.status === 200 && window.confirm('장바구니에 상품을 담았습니다. 장바구니로 이동하시겠습니까?')) {
    await router.push("/cart");
  }
};
</script>

<template>
  <div class="card shadow-sm"> <!-- 상품의 사진, 이름, 할인율, 금액 출력 -->
    <!-- 상품 사진 출력 -->
    <span class="img" :style="{backgroundImage: `url(${props.item.imgPath})`}"
          :aria-label="`상품 사진(${props.item.name})`"></span>
    <div class="card-body">
      <p class="card-text">
        <!-- 상품 이름 -->
        <span class="me-2">{{ props.item.name }}</span>
        <!-- 상품 할인율 -->
        <span class="discount badge bg-danger">{{ props.item.discountPer }}%</span>
      </p>
      <p class="card-text text-muted flex-grow-1 small">
        {{ props.item.comment }}
      </p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary btn-sm" @click="put()">장바구니 담기</button>
        <!-- 상품 정가(숫자 데이터에 3자리마다 쉼표 표기) -->
        <small class="price text-muted">{{ props.item.price.toLocaleString() }}원</small>
        <!-- 상품 할인가 -->
        <small class="real text-danger">{{ computedItemDiscountPrice }}</small>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.card {
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* 애니메이션 추가 */

  &:hover {
    transform: translateY(-7px); /* 마우스 올리면 위로 살짝 올라감 */
    box-shadow: 0 10px 20px rgba(0,0,0,0.15) !important; /* 그림자 더 진하게 */
  }

  .img {
    display: block;
    width: 100%;
    height: 250px;
    background-size: cover;
    background-position: center;
    border-top-left-radius: 0.375rem; /* 카드 상단 모서리 둥글게 */
    border-top-right-radius: 0.375rem;
  }

  .card-body {
    .price {
      text-decoration: line-through;
      font-size: 0.85rem;
    }
  }
}
</style>