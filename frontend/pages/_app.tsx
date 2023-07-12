import '../styles/globals.css'
import type {AppProps} from 'next/app'
import React from "react";
import Header from "../components/elements/common/Header";
import {Auth0Provider} from "@auth0/auth0-react";

function MyApp({Component, pageProps}: AppProps) {
    const redirectUrl = process.env.NEXT_PUBLIC_AUTH0_REDIRECT_URL!
    const domain = process.env.NEXT_PUBLIC_AUTH0_DOMAIN!
    const clientId = process.env.NEXT_PUBLIC_AUTH0_CLIENT_ID!

    return (
        <>
            <Auth0Provider
                domain={domain}
                clientId={clientId}
                authorizationParams={{
                  redirect_uri: redirectUrl,
                }}
            >
                <Header/>
                <Component {...pageProps} />
            </Auth0Provider>
        </>)
}

export default MyApp
