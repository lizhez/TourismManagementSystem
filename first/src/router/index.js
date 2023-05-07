import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from "@/components/Login";
import Vlog from "@/views/Vlog";
import Views from "@/views/Views";
import User from "@/views/User";
import Reformation from "@/views/Reformation";
import MyBuys from "@/views/MyBuys";
import BuyItem from "@/views/BuyItem";
import Comment from "@/views/Comment";
import Love from "@/views/Love";
import TalkWithPlat from "@/views/TalkWithPlat";
import Article from "@/views/Article";
import Item from "@/views/Item";
import Index from "@/views/Index";
import Public from "@/views/Public";
import Edit from "@/views/Edit";
import TheBuys from "@/views/TheBuys";
import Load from "@/views/Load";
import NewItem from "@/views/NewItem";
import EditItem from "@/views/EditItem";

const routes = [
  {
    path: '/',
    redirect:'/main',
  },
  {
    path: '/main',
    name: 'Login',
    component: Login,
  },
  {
    path: '/main/home',
    name: 'Home',
    component: Home,
    props:true,
    children: [
      {
        path: '/main/index',
        name: 'Index',
        component: Index,
      },
      {
        path: '/main/views',
        name: 'Views',
        component: Views
      },
      {
        path: '/main/vlog',
        name: 'Vlog',
        component: Vlog
      },
      {
        path: '/main/article/:id',
        name: 'Article',
        component: Article,
        props: true,
      },
      {
        path: '/main/item/:id',
        name: 'Item',
        component: Item,
        props: true,
      },
      {
        path: '/main/user',
        name: 'User',
        component: User,
        children:[
          {
            path: '/main/user/ref',
            name: 'Reformation',
            component: Reformation,
          },
          {
            path: '/main/user/mybuys',
            name: 'MyBuys',
            component: MyBuys,
          },
          {
            path: '/main/user/thebuys',
            name: 'TheBuys',
            component: TheBuys,
          },
          {
            path: '/buyItem/:id',
            name: 'BuyItem',
            component: BuyItem,
            props: true,
          },
          {
            path: '/main/user/comment/:id',
            name: 'Comment',
            component: Comment,
            props: true,

          },
          {
            path: '/main/user/public/:id',
            name: 'Public',
            component: Public,
            props: true,
          },
          {
            path: '/main/user/load/:id',
            name: 'Load',
            component: Load,
            props: true,
          },
          {
            path: '/main/user/edit/:id',
            name: 'Edit',
            component: Edit,
            props: true,
          },
          {
            path: '/main/user/newItem/:id',
            name: 'NewItem',
            component: NewItem,
            props: true,
          },
          {
            path: '/main/user/editItem/:id',
            name: 'EditItem',
            component: EditItem,
            props: true,
          },
          {
            path: '/main/user/love',
            name: 'Love',
            component: Love
          },
          {
            path: '/main/user/plat',
            name: 'TalkWithPlat',
            component: TalkWithPlat
          },
        ],
      },
    ],
  },


]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
