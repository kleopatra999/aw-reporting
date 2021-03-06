// Copyright 2013 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.api.ads.adwords.awreporting.server.rest;

import com.google.api.ads.adwords.awreporting.server.entities.Account;

import org.restlet.representation.Representation;

import java.util.List;

/**
 * Rest entry point for Account objects
 */
public class AccountRest extends AbstractBaseResource {

  public Representation getHandler() {
    String result = null;

    try {
      RestServer.getWebAuthenticator().checkAuthentication();

      Long topAccountId = getParameterAsLong("topAccountId");
      Integer offset = getParameterAsInteger("offset");
      Integer limit = getParameterAsInteger("limit");

      if (topAccountId != null ) {
        // Check that the user owns that MCC
        RestServer.getWebAuthenticator().checkAuthentication(topAccountId);

        List<Account> listAccounts;
        if (offset != null && limit !=null) {
          listAccounts = RestServer.getPersister().get(Account.class, Account.TOP_ACCOUNT_ID, topAccountId, offset, limit);
        } else {
          listAccounts = RestServer.getPersister().get(Account.class, Account.TOP_ACCOUNT_ID, topAccountId);
        }

        result =  gson.toJson(listAccounts);
      }

    } catch (Exception exception) {
      return handleException(exception);
    }

    addReadOnlyHeaders();
    return createJsonResult(result);
  }
}
