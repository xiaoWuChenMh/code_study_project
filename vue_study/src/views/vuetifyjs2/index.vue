<template>
    <v-app>
        <head-view/>
        <drawer-view/>
        <v-main>
            <v-banner elevation="24" icon="$mdiVuetify" :justify="end"></v-banner>
            <router-view />
        </v-main>
    </v-app>
</template>


<script>
    export default {
        name: 'HomeIndex',

        components: {
            HeadView: () => import('./head'),//头部
            DrawerView: () => import('./drawer'), // 左侧
            // //  DashboardCoreSettings: () => import('./components/core/Settings'),
            // DashboardCoreView: () => import('./components/core/View'),//底部
        },

        data: () => ({
            expandOnHover: false,
        }),

    }
</script>
