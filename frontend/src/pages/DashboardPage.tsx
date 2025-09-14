import React from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import {
  ServerIcon,
  ArrowPathIcon,
  PlusIcon,
  ChartBarIcon,
} from '@heroicons/react/24/outline';

export const DashboardPage: React.FC = () => {
  const { t } = useTranslation();

  const stats = [
    {
      name: t('dashboard.stats.totalServices'),
      value: '0',
      icon: ServerIcon,
      color: 'bg-blue-500',
      href: '/services',
    },
    {
      name: t('dashboard.stats.totalRoutes'),
      value: '0',
      icon: ArrowPathIcon,
      color: 'bg-green-500',
      href: '/services',
    },
    {
      name: t('dashboard.stats.totalUpstreams'),
      value: '0',
      icon: ChartBarIcon,
      color: 'bg-purple-500',
      href: '/upstreams',
    },
    {
      name: t('dashboard.stats.totalTargets'),
      value: '0',
      icon: ServerIcon,
      color: 'bg-orange-500',
      href: '/upstreams',
    },
  ];

  const quickActions = [
    {
      name: t('services.addService'),
      description: 'Create a new service',
      href: '/services',
      icon: PlusIcon,
      color: 'bg-blue-500',
    },
    {
      name: t('upstreams.addUpstream'),
      description: 'Create a new upstream',
      href: '/upstreams',
      icon: PlusIcon,
      color: 'bg-green-500',
    },
  ];

  return (
    <div>
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-gray-900">{t('dashboard.title')}</h1>
        <p className="mt-1 text-sm text-gray-500">{t('dashboard.welcome')}</p>
      </div>

      {/* Stats */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4 mb-8">
        {stats.map((stat) => {
          const Icon = stat.icon;
          return (
            <Link
              key={stat.name}
              to={stat.href}
              className="relative bg-white pt-5 px-4 pb-12 sm:pt-6 sm:px-6 shadow rounded-lg overflow-hidden hover:shadow-md transition-shadow"
            >
              <dt>
                <div className={`absolute ${stat.color} rounded-md p-3`}>
                  <Icon className="h-6 w-6 text-white" />
                </div>
                <p className="ml-16 text-sm font-medium text-gray-500 truncate">{stat.name}</p>
              </dt>
              <dd className="ml-16 pb-6 flex items-baseline sm:pb-7">
                <p className="text-2xl font-semibold text-gray-900">{stat.value}</p>
              </dd>
            </Link>
          );
        })}
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Quick Actions */}
        <div className="bg-white shadow rounded-lg">
          <div className="px-4 py-5 sm:p-6">
            <h3 className="text-lg leading-6 font-medium text-gray-900 mb-4">
              {t('dashboard.quickActions')}
            </h3>
            <div className="space-y-3">
              {quickActions.map((action) => {
                const Icon = action.icon;
                return (
                  <Link
                    key={action.name}
                    to={action.href}
                    className="flex items-center p-3 rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors"
                  >
                    <div className={`flex-shrink-0 ${action.color} rounded-md p-2`}>
                      <Icon className="h-5 w-5 text-white" />
                    </div>
                    <div className="ml-3">
                      <p className="text-sm font-medium text-gray-900">{action.name}</p>
                      <p className="text-sm text-gray-500">{action.description}</p>
                    </div>
                  </Link>
                );
              })}
            </div>
          </div>
        </div>

        {/* Recent Activity */}
        <div className="bg-white shadow rounded-lg">
          <div className="px-4 py-5 sm:p-6">
            <h3 className="text-lg leading-6 font-medium text-gray-900 mb-4">
              {t('dashboard.recentActivity')}
            </h3>
            <div className="text-center py-8">
              <ChartBarIcon className="mx-auto h-12 w-12 text-gray-400" />
              <h3 className="mt-2 text-sm font-medium text-gray-900">No recent activity</h3>
              <p className="mt-1 text-sm text-gray-500">Get started by creating a service or upstream.</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
