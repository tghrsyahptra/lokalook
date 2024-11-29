<?php

use Illuminate\Foundation\Application;
use Illuminate\Foundation\Configuration\Exceptions;
use Illuminate\Foundation\Configuration\Middleware;

$app = Application::configure(basePath: dirname(__DIR__))
    ->withRouting(
        web: __DIR__.'/../routes/web.php',
        commands: __DIR__.'/../routes/console.php',
        health: '/up',
    )
    ->withMiddleware(function (Middleware $middleware) {
        //
    })
    ->withExceptions(function (Exceptions $exceptions) {
        //
    })->create();

/*
|--------------------------------------------------------------------------
| Set Storage Path
|--------------------------------------------------------------------------
|
| This script allows you to override the default storage location used by
| the  application.  You may set the APP_STORAGE environment variable
| in your .env file,  if not set the default location will be used
|
*/
$app->useStoragePath(env('APP_STORAGE', base_path() . '/storage'));

return $app;
