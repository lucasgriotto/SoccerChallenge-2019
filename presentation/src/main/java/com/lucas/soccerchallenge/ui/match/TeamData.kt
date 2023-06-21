package com.lucas.soccerchallenge.ui.match

object TeamData {

    // Don't have an endpoint to get team logos, had to hardcode
    val teamLogosMap by lazy {
        mapOf(
                1 to "https://1.bp.blogspot.com/-Ng7ksc11IZE/WVLsI0mBJzI/AAAAAAABJ_A/1d7FgNeG6lMRAyJC6r90E8duWZy_EcUywCLcBGAs/s1600/Manchester%2BUnited%2BFC.png",
                3 to "https://1.bp.blogspot.com/--Bn_2NcIWVE/XwDwFTUD3qI/AAAAAAABeaU/i16bFDK-XGYYGvH-k80s9hd_04TqbWYnwCK4BGAsYHg/d/Arsenal%2BFC.png",
                4 to "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjzDiQJeHSrOIT3iGoSUYZ18bmVE2kg0yKE2OKryj4KsHaSZDMiIxIqrUJ0lqn2_X740mWXTcKybc1mCcI0QNEyinjBVRoD_Jul7wpeeKkBTOEYH07zYBADPL2HRqR4bspOu_kpNzZZh9UkoYA9kIMf0ekEr9eYPqTeU28h7g1XRaHq6r8Ja9OhxSA7/s16000/Newcastle%20United%20FC.png",
                6 to "https://1.bp.blogspot.com/-VrhLw1-JOiI/Xm2BFAKLIYI/AAAAAAABYKk/4jUCwHmgxEklpWuE9pAOP78xH18kv3KQgCLcBGAsYHQ/s1600/Tottenham%2BHotspur%2BFC.png",
                8 to "https://1.bp.blogspot.com/-pp2lDtQfGBQ/XsMFQiDrqrI/AAAAAAABa58/F9iqB_cey5IcPPEyVv_apqHRFYNB9slYgCK4BGAsYHg/d/Chelsea%2BFC.png",
                11 to "https://3.bp.blogspot.com/-6-FdZd_2iOo/WVLp8oqT3VI/AAAAAAABJ-A/pUlg3dRARm00eXprrg86j_zhMFwxmch8gCLcBGAs/s1600/Everton%2BFC.png",
                13 to "https://2.bp.blogspot.com/-Y-2jWNjLcfU/WVLq0rkC59I/AAAAAAABJ-c/-om4_ykR8lslo5F7wb8R_1QLmq1agC-qACLcBGAs/s1600/Leicester%2BCity%2BFC.png",
                14 to "https://4.bp.blogspot.com/-v2WymUTwu58/WVLrUnFC19I/AAAAAAABJ-o/wYadkzaNNkkv9VtC5EI-q9i0BRE4iEpxgCLcBGAs/s1600/Liverpool%2BFC.png",
                17 to "https://3.bp.blogspot.com/-8qiWnykcqzM/XKVbuImwB1I/AAAAAAABUzk/IzWGeygXffIxW3yoF5oFoGkVJOW_GtyVgCLcBGAs/s1600/Nottingham%2BForest%2BFC.png",
                19 to "https://2.bp.blogspot.com/-7CocxxKB5bM/WVKFYoGXyzI/AAAAAAABJ78/UIaKEyKwJmkmgP4K8YlOxmgMM0zifZ9OQCLcBGAs/s1600/Sheffield%2BWednesday%2BFC.PNG",
                20 to "https://1.bp.blogspot.com/-P6Ga_qFT7qI/WVLtKqfVnWI/AAAAAAABJ_c/mMSvpmngHYYoFHs-9JcnDUpuh6jhL0IjwCLcBGAs/s1600/Southampton%2BFC.png",
                21 to "https://1.bp.blogspot.com/-4LePL5lnlus/Xrec9dJ8WiI/AAAAAAABaHg/ad1XSCeZB-8EPgGENPqn4rfw9N8m43wzwCLcBGAsYHQ/s1600/West%2BHam%2BUnited%2BFC.png",
                31 to "https://1.bp.blogspot.com/-ExL70PeOorQ/Xt7nH2yj3SI/AAAAAAABczw/RiaFo1q7WjcBx6tyaUkCmsfp_LibBiaggCK4BGAsYHg/d/Crystal%2BPalace%2BFC.png",
                36 to "https://4.bp.blogspot.com/-cC1b51S1YE0/WVLnaqEelpI/AAAAAAABJ9E/_qzEtcwxXdcT3XW1wfS8B4Ye8RPZY9pHQCLcBGAs/s1600/Brighton%2BHove%2BAlbion.png",
                38 to "https://1.bp.blogspot.com/-4YzEVy-ueD4/XcI6ZRRjp5I/AAAAAAABWlo/oCW2n7eDNAgL4htUtjG9bud7O4VRFiXBgCLcBGAsYHQ/s1600/Huddersfield%2BTown%2BFC.png",
                39 to "https://4.bp.blogspot.com/-C_FNMkIvAPI/WcWn1bUGlTI/AAAAAAABPRg/LS4K3hazIPwUbvEsuwYfhRZiAOekElgNACLcBGAs/s1600/Wolverhampton%2BWanderers%2BFC.PNG",
                43 to "https://2.bp.blogspot.com/-wNBMww1sX6U/WVLrzZoFj8I/AAAAAAABJ-0/5WoOFPvexHsMGrkhDRNM_6CtXxx0vdwbACLcBGAs/s1600/Manchester%2BCity%2BFC.png",
                54 to "https://4.bp.blogspot.com/-_y06xiNjNjI/WVJ6h0AyTGI/AAAAAAABJ5A/EV-Sx16naJMsgElWWAYC7LY5hop348XGQCLcBGAs/s1600/Fulham%2BFC.png",
                57 to "https://1.bp.blogspot.com/-BrfIt6tl-fA/WVLvSQGaUGI/AAAAAAABKAc/2VMvDbN7cwcdr08Vu3cqDDQg5KHZBayowCLcBGAs/s1600/Watford%2BFC.png",
                90 to "https://1.bp.blogspot.com/-JrCU6KnIpYs/WVLoc2gTyAI/AAAAAAABJ9Y/Hv5-SRhfDwEgJQmYEc95WkfUUbAjRehVwCLcBGAs/s1600/Burnley%2BFC.png",
                91 to "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjyPO8Ap4I0w5h2s2W1NkLhz5B7pgPWZSXFMFKoH30XmMuPRZI2zzMNDxTxlTOD1Xi8ZmQZFaPSUWGUW7wiWhjbm3ZaZSBdhqcDY5M89xS8wkBajsxBGezy3zYAgOhtbBRfVoazBJzfL7sE2Dp2zA83rAe6z8dFdsW40nCEYuTcAA9idEJTtkEli279/s16000/AFC%20Bournemouth.png",
                97 to "https://1.bp.blogspot.com/-Fctgq6m0q-U/WVJ2HAX3ehI/AAAAAAABJ4c/r3u9t2yJifoLxpR_PY4d3hnHZH3-F3RKgCLcBGAs/s1600/Cardiff%2BCity%2BFC.png",
                993 to "https://1.bp.blogspot.com/-_NwrWI8WkgU/W7gzgQq8ttI/AAAAAAABSLE/rdxjnRY2mgI1xD6jW9G_5CfeaT9uKbhQQCLcBGAs/s1600/Malmo%2BFF.png",
                2714 to "https://1.bp.blogspot.com/-FTfZ_3-tAec/XStT_JpcelI/AAAAAAABVoM/gvz9nA2YyYowQDhTlL-F29503abXAnwBgCLcBGAs/s1600/MOL%2BFehervar%2BFC.png"
        )
    }

    // Don't have an endpoint to get team website url, had to hardcode
    val teamUrlMap by lazy {
        mapOf(
                1 to "https://www.manutd.com",
                3 to "https://www.arsenal.com",
                4 to "https://www.nufc.co.uk",
                6 to "https://www.tottenhamhotspur.com",
                8 to "https://www.chelseafc.com",
                11 to "https://www.evertonfc.com",
                13 to "https://www.lcfc.com",
                14 to "https://www.liverpoolfc.com",
                17 to "https://www.nottinghamforest.co.uk",
                19 to "https://www.swfc.co.uk",
                20 to "https://www.southamptonfc.com",
                21 to "https://www.whufc.com",
                31 to "https://www.cpfc.co.uk",
                36 to "https://www.brightonandhovealbion.com",
                38 to "https://www.htafc.com",
                39 to "https://www.wolves.co.uk",
                43 to "https://www.mancity.com",
                54 to "https://www.fulhamfc.com",
                57 to "https://www.watfordfc.com",
                90 to "https://www.burnleyfootballclub.com",
                91 to "https://www.afcb.co.uk",
                97 to "https://www.cardiffcityfc.co.uk",
                993 to "https://www.mff.se",
                2714 to "https://www.molfehervarfc.hu"
        )
    }

}
