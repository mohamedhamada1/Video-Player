/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sa.waqood.networkmodule;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import sa.waqood.networkmodule.enums.Status;

import static sa.waqood.networkmodule.enums.Status.ERROR;
import static sa.waqood.networkmodule.enums.Status.LOADING;
import static sa.waqood.networkmodule.enums.Status.SUCCESS;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Resource<T> {

    // response status 'loading , error, success'
    @Nullable
    public final Status status;

    // in case error will be with this error throwable
    @Nullable
    public final Throwable throwable;

    // error message that come in throwable
    @Nullable
    public String message = null;

    // request id
    public int requestId;
    // data that come in case success
    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable throwable, int requestId) {
        this.status = status;
        this.throwable = throwable;
        this.data = data;
        this.requestId = requestId;
        if (status == ERROR)
            message = ErrorChecker.getErrorMsg(throwable);
    }

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Throwable throwable) {
        this.status = status;
        this.throwable = throwable;
        this.data = data;
        if (status == ERROR)
            message = ErrorChecker.getErrorMsg(throwable);
    }

    public static <T> Resource<T> success(@Nullable T data, int requestId) {
        return new Resource<>(SUCCESS, data, null, requestId);
    }

    public static <T> Resource<T> error(@Nullable T data, @Nullable Throwable throwable, int requestId) {
        return new Resource<>(ERROR, data, throwable, requestId);
    }

    public static <T> Resource<T> noInternet(@Nullable Throwable throwable, int requestId) {

        return new Resource<>(ERROR, null, throwable, requestId);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) {
            return false;
        }

        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

}
