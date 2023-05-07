import { createRouter, createWebHistory } from 'vue-router'
import User from '../views/User.vue'
import Hotel from "@/views/Hotel";
import Travel from "@/views/Travel";
import Comment from "@/views/Comment";
import Buy from "@/views/Buy";
import FinishBuy from "@/views/FinishBuy";
import Top from "@/views/Top";
import Img from "@/views/Img";
import Text from "@/views/Text";
import Article from "@/views/Article";
import Main from "@/views/Main";
import TravelItem from "@/views/TravelItem";
import BuyItem from "@/views/BuyItem";
import Req from "@/views/Req";

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/user',
    name: 'User',
    component: User
  },
  {
    path: '/hotel',
    name: 'Hotel',
    component: Hotel
  },
  {
    path: '/req',
    name: 'Req',
    component: Req
  },
  {
    path: '/travel',
    name: 'Travel',
    component: Travel
  },
  {
    path: '/travelItem/:id',
    name: 'TravelItem',
    props:true,
    component: TravelItem
  },
  {
    path: '/comment',
    name: 'Comment',
    component: Comment
  },
  {
    path: '/buy',
    name: 'Buy',
    component: Buy,
  },
  {
    path: '/finishBuy',
    name: 'FinishBuy',
    component: FinishBuy
  },
  {
    path: '/buyItem/:id',
    name: 'BuyItem',
    component: BuyItem,
    props: true,
  },
  {
    path: '/top',
    name: 'Top',
    component: Top
  },
  {
    path: '/img/',
    name: 'Img',
    component: Img
  },
  {
    path: '/text',
    name: 'Text',
    component: Text
  },
  {
    path: '/article/:id',
    name: 'Article',
    component: Article,
    props: true,
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
