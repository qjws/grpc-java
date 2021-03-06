/*
 * Copyright 2018 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.grpc.services.internal;

import io.grpc.Internal;
import io.grpc.LoadBalancer;
import io.grpc.LoadBalancer.Helper;
import io.grpc.LoadBalancerProvider;
import io.grpc.internal.RoundRobinLoadBalancerProvider;
import io.grpc.services.HealthCheckingLoadBalancerUtil;

/**
 * The health-check-capable provider for the "round_robin" balancing policy.  This overrides
 * the "round_robin" provided by grpc-core.
 */
@Internal
public final class HealthCheckingRoundRobinLoadBalancerProvider extends LoadBalancerProvider {
  private final RoundRobinLoadBalancerProvider rrProvider = new RoundRobinLoadBalancerProvider();

  @Override
  public boolean isAvailable() {
    return rrProvider.isAvailable();
  }

  @Override
  public int getPriority() {
    return rrProvider.getPriority() + 1;
  }

  @Override
  public String getPolicyName() {
    return rrProvider.getPolicyName();
  }

  @Override
  public LoadBalancer newLoadBalancer(Helper helper) {
    return HealthCheckingLoadBalancerUtil.newHealthCheckingLoadBalancer(rrProvider, helper);
  }
}
