import Vue from 'vue'
import VueRouter from 'vue-router'

import MyRegister from "@/pager/MyRegister";
import myLogin from "@/pager/MyLogin";
import myLawyerRegister from "@/pager/MyLawyerRegister";

//后台管理页面
import myAdmin from "@/pager/admin/MyAdmin";
import MyAdmins from "@/pager/admin/MyAdmins";
import MyUsers from "@/pager/admin/MyUsers";
import MyLawyers from "@/pager/admin/lawyerTab/MyLawyers";
import MyLegals from "@/pager/admin/MyLegal";
import MyProblem from "@/pager/admin/MyProblem";

import LawyerContent from "@/pager/admin/lawyerTab/LawyerContent";
import LawyerDecide from "@/pager/admin/lawyerTab/LawyerDecide";
import LawyerSort from "@/pager/admin/lawyerTab/LawyerSort";

//用户页面
import UserNews from "@/pager/user/UserNews";
import UserIndexs from "@/pager/user/UserIndexs";
import UserLawyers from "@/pager/user/UserLawyers";
import UserQuestions from "@/pager/user/UserQuestions";
import UserPager from "@/pager/user/UserPager";
import userBeforeQuestions from "@/pager/user/UserBeforeQuestions";

//律师页面
import LawyerPager from "@/pager/lawyer/LawyerPager";
import LawyerIndex from "@/pager/lawyer/LawyerIndex";
import LawyerBeforeQuestions from "@/pager/lawyer/LawyerBeforeQuestions";
import LawyerQuestions from "@/pager/lawyer/LawyerQuestions";
import LawyerLegal from "@/pager/lawyer/LawyerLegal";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'MyLogin',
        component: myLogin
    },
    {
        path: "/register",
        name: 'register',
        component: MyRegister,
    },
    {
        path: "/registerLawyer",
        name: "lawyerRegister",
        component: myLawyerRegister
    },
    {
        path: "/admin",
        name: 'myAdmin',
        component: myAdmin,
        children: [
            {
                path: '/admins',
                name: 'admins',
                component: MyAdmins
            },
            {
                path: '/users',
                name: 'users',
                component: MyUsers
            },
            {
                path: '/lawyers',
                name: 'lawyers',
                component: MyLawyers,
                children:[
                    {
                        path:'/content',
                        name:'content',
                        component:LawyerContent
                    },
                    {
                        path:'/sort',
                        name:'sort',
                        component:LawyerSort
                    },
                    {
                        path:'/decide',
                        name:'decide',
                        component:LawyerDecide
                    }
                ]
            },
            {
                path: '/legals',
                name: 'legals',
                component: MyLegals
            },
            {
                path: '/problems',
                name: 'problems',
                component: MyProblem
            }
        ]
    },
    {
        path: "/user",
        name: 'user',
        component: UserPager,
        redirect: '/indexs',
        children: [
            {
                path: '/indexs',
                name: "indexs",
                component: UserIndexs
            },
            {
                path: '/news',
                name: "news",
                component: UserNews
            },
            {
                path: '/lawyerTeams',
                name: "lawyerTeams",
                component: UserLawyers
            },
            {
                path: "/beforeUser",
                name:"lawyerBeforeQuestions",
                component: userBeforeQuestions
            },
            {
                path: "/questions/:toName",
                name: "questions",
                component: UserQuestions
            }
        ]
    },
    {
        path:'/lawyer',
        name: "lawyer",
        component: LawyerPager,
        redirect: '/lawyerIndex',
        children: [
            {
                path: "/before",
                name:"lawyerBeforeQuestions",
                component: LawyerBeforeQuestions
            },
            {
                path: "/lawyerIndex",
                component: LawyerIndex,
                name: 'lawyerIndex'
            },
            {
                path: "/lawyerQuestions/:toName",
                component: LawyerQuestions,
                name: 'lawyerQuestions'
            },
            {
                path: "/lawyerLegals",
                component: LawyerLegal,
                name: 'lawyerLegals'
            }
        ]
    }
]

const router = new VueRouter({
    routes
})

export default router
