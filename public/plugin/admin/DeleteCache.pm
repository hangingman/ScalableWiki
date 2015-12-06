###############################################################################
#
# キーワードキャッシュを削除します。
#
###############################################################################
package plugin::admin::DeleteCache;
use strict;

#==============================================================================
# コンストラクタ
#==============================================================================
sub new {
	return bless {}, shift;
}

#==============================================================================
# フック
#==============================================================================
sub hook {
	my (undef, $wiki) = @_;
	my $log_dir = $wiki->config('log_dir');
	my @cachefiles = ('keywords2.cache', 'keywords.cache');

	# キャッシュファイルがあれば削除。
	foreach my $cache (@cachefiles) {
		my $cachefile = $log_dir . '/' . $cache;
		unlink $cachefile if (-e $cachefile);
	}
}

1;